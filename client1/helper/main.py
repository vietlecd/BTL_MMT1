import requests
import hashlib
import requests

def fetch_all_file(base_url):
    response = requests.get(f'{base_url}/file/fetch_all')
    return response

def publishFileOrg(server_urls, info_hash, file_name, file_size, address, port):
    for server_url in server_urls:
        try:
            requests.post(f'{server_url}/file/publish', data = { 'hashInfo': info_hash, 'name': file_name, 'size': file_size, 'peerAddress': address, 'peerPort': port })
        except:
            pass

def generate_info_hash(file_path, hash_algorithm = 'sha1'):
    if hash_algorithm == 'sha1':
        hash_func = hashlib.sha1()
    elif hash_algorithm == 'sha256':
        hash_func = hashlib.sha256()
    else:
        raise ValueError("Unsupported hash algorithm. Use 'sha1' or 'sha256'.")

    with open(file_path, 'rb') as file:
        while chunk := file.read(4096):
            hash_func.update(chunk)

    return hash_func.hexdigest()

def fetch_file_by_info_hash(base_url, info_hash):
    try:
        return requests.get(f'{base_url}/file/fetch?hasInfo={info_hash}')
    except:
        return None

import requests

def get_file_info_and_peers_keep_file_from_trackers(info_hash, tracker_urls):
    peers_keep_files = []
    file_name = None
    file_size = None

   

    for tracker_url in tracker_urls:
        try:
            # Gửi yêu cầu với token
            response = requests.get(f"{tracker_url}files/fetch?hashInfo={info_hash}")
            if response.status_code == 200:
                data = response.json()
                if isinstance(data, dict):
                    # Lấy thông tin file
                    file_name = data.get("fileName", "Unknown")
                    file_size = data.get("fileSize", "Unknown")

                    # Lấy danh sách các peer
                    peers = data.get("peers", [])
                    if isinstance(peers, list):
                        for peer in peers:
                            # Xử lý key bất thường
                            address = peer.get("address", peer.get("address: ", "Unknown")).strip()
                            port = peer.get("port", peer.get("port: ", "Unknown"))

                            # Kiểm tra kiểu dữ liệu hợp lệ
                            if isinstance(address, str) and isinstance(port, int):
                                peers_keep_files.append((address, port))
                else:
                    print("Định dạng phản hồi từ tracker không đúng.")
            else:
                print(f"Không thể lấy dữ liệu từ {tracker_url}. Mã trạng thái: {response.status_code}, Phản hồi: {response.text}")

        except requests.exceptions.RequestException as e:
            print(f"Lỗi xảy ra khi kết nối với {tracker_url}: {e}")

    return file_name, file_size, set(peers_keep_files)



def announce_downloaded(base_url, info_hash, file_name, file_size, peer_address, peer_port, status):
    

    # Kiểm tra nếu base_url là danh sách
    if isinstance(base_url, list):
        base_url = base_url[0]  # Lấy chuỗi đầu tiên trong danh sách

    # Headers với token
   

    try:
        # Gửi yêu cầu POST với JSON payload
        response = requests.post(
            f'{base_url}files/peers/announce',
            
            json={
                'infoHash': info_hash,
                'peerAddress': peer_address,
                'peerPort': peer_port,
                'status': status
            }
        )
        return response
    except requests.exceptions.RequestException as e:
        print(f"An error occurred while sending the request: {e}")
        return None

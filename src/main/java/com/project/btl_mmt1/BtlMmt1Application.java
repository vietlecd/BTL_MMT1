package com.project.btl_mmt1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BtlMmt1Application {

	public static void main(String[] args) {

		SpringApplication.run(BtlMmt1Application.class, args);

//		PeerServerService peerServerService = new PeerServerService();
//		new Thread(peerServerService::startServer).start();
	}

}

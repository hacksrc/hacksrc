#!/bin/python

import socket
import sys

def parse_msg_header(hdr):
	for byte in hdr:
		print(byte)

if __name__ == "__main__":
	# program debug specific settings
	mDebug = 1

	# server settings
	mSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	mServerIpAddress = ('192.168.1.97', 1337)
	mSocket.bind(mServerIpAddress)

	# server messaging
	mMsgHeaderBuf = bytearray(32)

	# start the server
	mSocket.listen(1)

	while True:
		if mDebug:
			print("waiting for a DM")
		# accept the DM client
		mConnection, mClientAddress = mSocket.accept()

		# wait for a message
		if mDebug:
			print("waiting for DM data")
		data = mConnection.recv(32)

		# parse the header

		# get more data

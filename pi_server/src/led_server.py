#!/bin/python

# tcp settings
import socket
import sys

# data packing
from struct import *

# program debug specific settings
mDebug = 1

# message constants
mHdrMsgLen = 20
mHdrMsgMagic = 7
mHdrMsgHeartbeatId = 0
mHdrMsgEffectId = 1
mHdrMsgVersionNum = 0
mHdrMsgPayloadLen = 3

def parse_msg_payload(msg_id, effect, color, duration):
	print "python"
	if (msg_id == mHdrMsgHeartbeatId):
		# send in heartbeat
		print("SERV: got heartbeat")
		return True
	elif (msg_id == mHdrMsgEffectId):
		# send in led command
		print("SERV: LED command: effect={}, color={}, duration={}".format(effect, color, duration))
		return True
	else:
		# send in led command
		print("SERV: unsupported command")
		return False

def validate_msg_hdr(inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len):
	print "hello"
	if inp_msg_hdr != mHdrMsgMagic:
		print("SERV: invalid message header: {} != {}".format(inp_msg_hdr, mHdrMsgMagic))
		return False
	if (inp_msg_id != mHdrMsgHeartbeatId) and (inp_msg_id != mHdrMsgEffectId):
		print("SERV: invalid message id: {}".format(inp_msg_id))
		return False
	if inp_msg_ver != mHdrMsgVersionNum:
		print("SERV: invalid message header: {} != {}".format(inp_msg_ver, mHdrMsgVersionNum))
		return False
	if inp_msg_len != mHdrMsgPayloadLen:
		print("SERV: invalid message payload length: {} != {}".format(inp_msg_len, mHdrMsgPayloadLen))
		return False
	print "world"
	return True


def parse_msg(hdr):
	if len(hdr) < mHdrMsgLen:
		print("SERV: input data is invalid for a header {} != {}".format(len(hdr), mHdrMsgLen))
		return False

	try:
		# read out the header data
		inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len, effect, color, duration = unpack('>IIIQIIII', hdr)

		if mDebug:
			print("SERV: found header: {}, {}, {}, {}, {}".format(inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len))

		if validate_msg_hdr(inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len):
			return parse_msg_payload(inp_msg_id, effect, color, duration)

	except:
		print("SERV: failed to parse input data: len={}".format(len(hdr)))
		return False


if __name__ == "__main__":

	# server settings
	mSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	mServerIpAddress = ('192.168.1.97', 1337)
	mSocket.bind(mServerIpAddress)

	# server messaging
	mMsgHeaderBuf = bytearray(35)

	# start the server
	mSocket.listen(1)

	# accept the DM client
	if mDebug:
		print("SERV: waiting for a DM")
	mConnection, mClientAddress = mSocket.accept()

	while True:

		# wait for a message
		if mDebug:
			print("SERV: waiting for DM header")
		data = mConnection.recv(48)

		file = open("server.hex", "w")
		file.write(data)
		file.close()

		# parse the header
		if mDebug:
			print("SERV: got DM message")
		if not parse_msg(data):
			print("SERV: failed to read data, closing server")
			mSocket.close()
			sys.exit()
		data = ""

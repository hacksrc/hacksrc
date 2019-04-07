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
mHdrMsgHdrLen = 24

def parse_msg_payload(msg_id, data):
	effect, color, duration = unpack_from('>III', data)
	if msg_id == mHdrMsgEffectId:
		print("SERV: effect={}, color={},  duration={}".format(effect, color, duration))
	return [effect, color, duration]

def validate_msg_hdr(inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len):
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
	return True

def get_hdr_msg_id(hdr):
	try:
		# read out the header data
		inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len = unpack_from('>IIIQI', hdr)
		return inp_msg_id
	except:
		print("SERV: failed to parse input data: len={}".format(len(hdr)))
		return -1

def valid_msg_hdr(hdr):
	if len(hdr) < mHdrMsgLen:
		print("SERV: input data is invalid for a header {} != {}".format(len(hdr), mHdrMsgLen))
		return False

	try:
		# read out the header data
		inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len = unpack_from('>IIIQI', hdr)

		if mDebug:
			print("SERV: found header: {}, {}, {}, {}, {}".format(inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len))

		return validate_msg_hdr(inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len)

	except:
		print("SERV: failed to parse input data: len={}".format(len(hdr)))
		return False

def find_msg_hdr(data):
	sub = data.find(b'\x00\x00\x00\x07')
	if sub != -1:
		if mDebug > 1:
			print("SERV: magic header found at: {}".format(sub))
	else:
		if mDebug > 2:
			print("SERV: magic header not in data")
	return sub


if __name__ == "__main__":

	# server settings
	mSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	mServerIpAddress = ('192.168.1.97', 1337)
	mSocket.bind(mServerIpAddress)

	# start the server
	mSocket.listen(1)

	# accept the DM client
	if mDebug:
		print("SERV: waiting for a DM")
	mConnection, mClientAddress = mSocket.accept()

	while True:

		# wait for a message
		if mDebug > 2:
			print("SERV: waiting for DM header")
		data = mConnection.recv(48)

		# search for the header 
		pos = find_msg_hdr(data)
		if pos == -1:
			if mDebug > 2:
				print("SERV: magic header not found")
			continue
		else:
			data = data[pos:]
			if mDebug:
				print("SERV: magic header not found")

		# parse the header
		if mDebug:
			print("SERV: got DM message")
		if not valid_msg_hdr(data):
			print("SERV: failed to parse header, ignoring")

		msg_id = get_hdr_msg_id(data)
		data = data[mHdrMsgHdrLen:]
		effect, color, duration = parse_msg_payload(msg_id, data)
		data = ""

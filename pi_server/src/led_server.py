#!/usr/bin/python

# tcp settings
import socket
import sys

# data packing
from struct import *

# LED Import libs used
import time
import random
import getopt
from neopixel import *

#Start up random seed
random.seed()

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

mHdrMsgMagicByte = b'\x00\x00\x00\x07'

# defines for the LEDs
mEffectCmdFire = 0
mEffectCmdLevel = 1

# LED strip configuration:
LED_COUNT      = 50      # Number of LED pixels.
LED_PIN        = 18      # GPIO pin connected to the pixels (must support PWM!).
LED_FREQ_HZ    = 800000  # LED signal frequency in hertz (usually 800khz)
LED_DMA        = 5       # DMA channel to use for generating signal (try 5)
LED_BRIGHTNESS = 128     # Set to 0 for darkest and 255 for brightest
LED_INVERT     = False   # True to invert the signal (when using NPN transistor level shift)

#Predefined Colors and Masks
OFF = Color(0,0,0)
WHITE = Color(255,255,255)
RED = Color(255,0,0)
GREEN = Color(0,255,0)
BLUE = Color(0,0,255)
PURPLE = Color(128,0,128)
YELLOW = Color(255,255,0)
ORANGE = Color(255,50,0)
TURQUOISE = Color(64,224,208)
RANDOM = Color(random.randint(0,255),random.randint(0,255),random.randint(0,255))

FIRE_COLORS = [RED, ORANGE]

#bitmasks used in scaling RGB values
REDMASK = 0b111111110000000000000000
GREENMASK = 0b000000001111111100000000
BLUEMASK = 0b000000000000000011111111

# Other vars
LIGHT_ARRAY_LENGTH = 50
LIGHTSHIFT = 0  #shift the lights down the strand to the other end 
FLICKERLOOP = 3  #number of loops to flicker

'''
	parse_msg_payload: parse out the payload data from the buffer
'''
def parse_msg_payload(msg_id, data):
	effect, color, duration = unpack_from('>IfI', data)
	if msg_id == mHdrMsgEffectId:
		print("SERV: effect={}, color={},  duration={}".format(effect, color, duration))
	return [effect, color, duration]

'''
	validate the header data against expected values
'''
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

'''
	get the header's message ID
'''
def get_hdr_msg_id(hdr):
	try:
		# read out the header data
		inp_msg_hdr, inp_msg_id, inp_msg_ver, inp_msg_ts, inp_msg_len = unpack_from('>IIIQI', hdr)
		return inp_msg_id
	except:
		print("SERV: failed to parse input data: len={}".format(len(hdr)))
		return -1

'''
	pull the header data in buffer hdr and validate the header message
'''
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

'''
	look through buffer data for our magic header
'''
def find_msg_hdr(data):
	sub = data.find(mHdrMsgMagicByte)
	if sub != -1:
		if mDebug > 1:
			print("SERV: magic header found at: {}".format(sub))
	else:
		if mDebug > 2:
			print("SERV: magic header not in data")
	return sub

'''
	color functions
'''
def fireWall(strip, duration):
  for i in range(0,LIGHT_ARRAY_LENGTH):
    strip.setPixelColor(i, RED)

  strip.show()

  endTime = time.time() + duration

  while (endTime > time.time()):
    randomLedIndex = random.randint(0,LIGHT_ARRAY_LENGTH)

    #turn off for a random short period of time
    strip.setPixelColor(randomLedIndex, OFF)
    strip.show()
    time.sleep(random.randint(10,50)/1000.0)
    random_fire_color = random.randint(0,1)
    strip.setPixelColor(randomLedIndex, FIRE_COLORS[random_fire_color])
    strip.show()

  #kill all lights
  for led in range(0, LIGHT_ARRAY_LENGTH):
    strip.setPixelColor(led+LIGHTSHIFT, OFF)
  strip.show()

def showPlayerColor(strip, color, duration):

  for i in range(0,LIGHT_ARRAY_LENGTH):
    strip.setPixelColor(i, color)
  
  strip.show()

  time.sleep(duration)

  #kill all lights
  for led in range(0,LIGHT_ARRAY_LENGTH):
    strip.setPixelColor(led+LIGHTSHIFT, OFF)
  strip.show()

def levelUp(strip, color, duration):

  for i in range(0,LIGHT_ARRAY_LENGTH):
    strip.setPixelColor(i, color)

  strip.show()

  endTime = time.time() + duration

  pixelIndex = 0
  forward = True
  while (endTime > time.time()):

    if (pixelIndex >= LIGHT_ARRAY_LENGTH-1):
      forward = False
    elif (pixelIndex <= 0):
      forward = True

    #turn off for a random short period of time
    strip.setPixelColor(pixelIndex, OFF)
    strip.setPixelColor(pixelIndex+1, OFF)
    strip.show()
    time.sleep(0.02)
    strip.setPixelColor(pixelIndex, color)
    strip.setPixelColor(pixelIndex+1, color)
    strip.show()

    if (forward):
      pixelIndex += 2
    else:
      pixelIndex -= 2


  #kill all lights
  for led in range(0, LIGHT_ARRAY_LENGTH):
    strip.setPixelColor(led+LIGHTSHIFT, OFF)
  strip.show()

def playerColorLookup(color):
    return {
        'red': RED,
        'green': GREEN,
        'blue': BLUE,
        'purple': PURPLE,
        'yellow': YELLOW,
        'orange': ORANGE,
        'turquoise': TURQUOISE
    }.get(color, WHITE) 

if __name__ == "__main__":

	# Create NeoPixel object with appropriate configuration.
	strip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)
	# Intialize the library (must be called once before other functions).
	strip.begin()

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
		
		# call a command
		if (effect == mEffectCmdFire):
			fireWall(strip, duration)
		elif (effect == mEffectCmdLevel):
			levelUp(strip, playerColorLookup(color), duration)
		elif (color != ''):
			showPlayerColor(strip, playerColorLookup(color), duration)
		else:
			print ("SERV: invalid effect!")
	
		data = ""

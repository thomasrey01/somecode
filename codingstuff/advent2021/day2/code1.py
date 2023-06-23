
pos = 0
depth = 0
aim = 0
in_file = open("input.txt", "r")
commands = in_file.readlines()
for com in commands:
    s = com.split(" ")
    if s[0] == "up":
        aim -= int(s[1])
    if s[0] == "down":
        aim += int(s[1])
    if s[0] == "forward":
        depth += aim * int(s[1])
        pos += int(s[1])
print(depth * pos)
    
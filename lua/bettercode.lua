local opentime = 3
local electro = 0
local gen = 0
local solar = 0
local input
outputs = {}
a[0] = "OFF"
a[1] = "ON"
while true do
    term.clear()
    term.setCursorPos(1, 1)
    print("Select the Assembly line you wish to turn on/off. Current lines are: Electronics, Generators, Solar")
    print("Electronics Assembly is currently: " .. a[electro])
    print("Generator Assembly is currently: " .. a[gen])
    print("Solar Assembly is currently: " .. a[solar])
    input = read()
    if input == "Electronics On" or input == "1 On" then
        printf("Electronics Assembly line switched on")
        electro = 1
        redstone.setBundledOutput("back",colors.white)
        sleep(1)
    elseif input == "Electronics Off" or input == "1 Off" then
        print("Electronics Assembly Line Switched Off")
        electro = 0
        redstone.setBundledOutput("back",0,colors.white)
        sleep(1)
    elseif input == "Generators On" or input == "2 On" then
        print("Generators Assembly Line Switched On")
        gen = 1
        redstone.setBundledOutput("back",colors.yellow)
        sleep(1)
    elseif input == "Generators Off" or input == "2 Off" then
        term.setCursorPos(1,4)
        print("Generators Assembly Line Switched Off")
        gen = 0
        redstone.setBundledOutput("back",0,colors.yellow)
        sleep(1)
    elseif input == "Solar On" or input == "3 On" then
        term.setCursorPos(1,4)
        print("Solar Assembly Line Switched On")
        solar = 1
        redstone.setBundledOutput("back",colors.red)
        sleep(1)
    elseif input == "Solar Off" or input == "3 Off" then
        term.setCursorPos(1,4)
        print("Solar Assembly Line Switched Off")
        solar = 0
        redstone.setBundledOutput("back",0,colors.red)
        sleep(1)
    elseif input == "All On" then
        term.setCursorPos(1,4)
        print("All Assembly Lines Switched On")
        electro = 0
        gen = 0
        solar = 0
        redstone.setBundledOutput("back",colors.white + colors.red + colors.yellow)
        sleep(1.5)
    elseif input == "All Off" then
        term.setCursorPos(1,4)
        electro = 1
        gen = 1
        solar = 1
        print("All Assembly Lines Switched Off")
        redstone.setBundledOutput("back",0)
        sleep(1.5)
    else
        print("Invalid Command. Please talk to your local technician about your lack of brains, or check your spelling")
        sleep(5)
    end

    

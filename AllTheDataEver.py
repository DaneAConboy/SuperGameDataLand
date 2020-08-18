import random
gameNames = []
devNames = []
charNames = []
namefix = []
charfix = []
gameXML = []
charXML = []
with open('TonsOfGameData.txt') as my_file:
    for line in my_file:
        gameNames.append(line)
with open('GameDevList.txt') as my_file:
    for line in my_file:
        devNames.append(line)
with open('CharacterList.txt') as my_file:
    for line in my_file:
        charNames.append(line)
with open('fixnames.txt') as my_file:
    for line in my_file:
        namefix.append(line)

with open('9000GamesForXML.txt') as my_file:
    for line in my_file:
        gameXML.append(line)

with open('7000CharactersForXML.txt') as my_file:
    for line in my_file:
        charXML.append(line)

#SQLinsert = "INSERT INTO games VALUES(" #PRINT ALL THE GAMES
#for x in range(142,2569):
#    upToName = SQLinsert + str(x) + ",'" + gameNames[random.randint(1,len(gameNames)-1)][:-1]
#    printFinal = upToName+"',"+str(random.randint(1997,2016))+",'"+devNames[random.randint(1,len(devNames)-1)][:-1]+"');"
#    print(printFinal)

#SQLinsert = "INSERT INTO char (chid, name,first_appearance) VALUES(" #PRINT ALL THE CHARACTERS
#for x in range(45,1254):
#    upToName = SQLinsert + str(x) + ",'" + charNames[random.randint(1,len(charNames)-1)][:-1]
#    printFinal = upToName+"','"+gameNames[random.randint(1,len(gameNames)-1)][:-1]+"');"
#    print(printFinal)

#SQLinsert = "INSERT INTO characters_in_games VALUES(" #PRINT CHARACTERS IN GAMES
#for x in range(45,1254):
#    for y in range(1,random.randint(2,11)):
#        printFinal = SQLinsert + str(x) + ","+str(random.randint(1,2569)) +");"
#        print(printFinal)

#SQLinsert = "INSERT INTO games_on_platform VALUES(" #PRINT GAMES ON PLATFORMS
#for x in range(142,2569):
#    printFinal = SQLinsert + str(random.randint(1,17)) + ","+str(x)+");"
#    print(printFinal)

#SQLinsert = "INSERT INTO ratings VALUES(" #PRINT GAMES ON PLATFORMS
#for x in range(142,2569):
#    printFinal = SQLinsert + str(x) + ","+str(random.uniform(1.0,9.8))[0:3]+ ","+str(random.randint(1,100))+");"
#    print(printFinal)
#print("butt")
#def find(s, ch):
#    return [i for i, ltr in enumerate(s) if ltr == ch]
#for line in namefix:
#    print(line[:find(line,'(')[1]][:-1]+"',"+str(random.randint(1997,2016))+",'"+devNames[random.randint(1,len(devNames)-1)][:-1]+"');")

#def find(s, ch):
#    return [i for i, ltr in enumerate(s) if ltr == ch]
#for line in charfix:
#    print(line[:find(line,',')[3]][:-1]+"','"+str(random.randint(1997,2016))+"');")

#count = 0
#innercount = 0
#currPlatId = 2
#for line in gameXML:
#    if(innercount == 700):
#        print("\t\t</games>\n</platform>\n")
#        innercount = 0
#        currPlatId += 1
#        if(currPlatId == 18):
#            currPlatId == 2
#    count += 1
#    if(innercount == 0):
#        print("<platform>\n\t<pid>"+str(currPlatId)+"</pid>\n\t\t<games>")
#    innercount += 1
#    print("\t\t\t<game><gid>"+str(9999+count)+"</gid><title>"+line[:-1]+"</title><year>"+str(random.randint(1997,2016))+"</year><lead>"+devNames[random.randint(1,len(devNames)-1)][:-1]+"</lead></game>")

#count = 0
#innercount = 0
#currGameId = random.randint(10001,19000)
#for line in charXML:
#    count += 1
#    print("\t<character><chid>"+str(10000+count)+"</chid><name>"+line[:-1]+"</name><firstap>"+str(random.randint(1997,2016))+"</firstap></character>")
    
count = 0
innercount = 0
currGameId = random.randint(10001,19000)
for line in charXML:
    if(innercount == 7):
        print("\t\t</characters>\n</game>\n")
        innercount = 0
        currGameId = random.randint(10001,19000)
    count += 1
    if(innercount == 0):
        print("<game>\n\t<gid>"+str(currGameId)+"</gid>\n\t\t<characters>")
    innercount += 1
    print("\t\t\t<character><cid>"+str(10000+count)+"</cid><name>"+line[:-1]+"</name></character>")

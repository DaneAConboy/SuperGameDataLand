sum = 0.0
counter = 0
with open('logTJ.txt') as aa:
    for line in aa:
        sum += float(line)
        counter += 1
avg = sum / counter
print(avg)

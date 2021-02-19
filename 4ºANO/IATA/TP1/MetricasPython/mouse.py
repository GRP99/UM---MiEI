from pynput.mouse import Listener, Button
from tempfile import mkstemp
from datetime import datetime
from threading import Thread
from shutil import move, copymode
from os import fdopen, remove
import math
import time


countR = 0
countL = 0
total_distance = 0
x_aux = 0
y_aux = 0
flag = False
counter = 1
count = 0


f = open('mouse.txt', 'wt')
f.write("Number of right clicks" + ": " + str(countR) + "\n" + "Number of left clicks" + ": " + str(countL) + "\n\n")
f.write("Total distance moved: " + str(total_distance) + "\n\n")
f.close()


#Count the number of clicks made every 60s
class counterF(Thread):
    def __init__(self):
        Thread.__init__(self)
    def run(self):
        global count
        while True:
            time.sleep(60)
            f = open('mouse.txt', 'a')
            now = datetime.now()
            current_time = now.strftime("%H:%M:%S")
            f.write(current_time + " It was made " + str(count) + " clicks" + "\n")
            f.close()
            count = 0

t = counterF()
t.start()


#Detect mouse move
def on_move(x, y):
    print("Pointer moved to {0}".format((x, y)))
    global flag, x_aux, y_aux, total_distance, counter
    print(str(x_aux) + " and " + str(y_aux))
    if (flag == True):
        f = open('mouse.txt', 'a')
        distance = math.sqrt(((x_aux - x) ** 2) + ((y_aux - y) ** 2))
        f.write(str(counter) + ": " + str(distance) + "\n")
        f.close()
        counter += 1
        print("Distance between moves = " + str(distance))
        replace('mouse.txt', "Total distance moved: " + str(total_distance),
                "Total distance moved: " + str(total_distance + math.sqrt(((x_aux - x) ** 2) + ((y_aux - y) ** 2))))
        total_distance += math.sqrt(((x_aux - x) ** 2) + ((y_aux - y) ** 2))
    if (flag == False): flag = True
    x_aux = x
    y_aux = y


#Detect mouse scroll
def on_scroll(x, y, dx, dy):
  print("Mouse scrolled at ({0}, {1})({2}, {3})".format(x, y, dx, dy))


#Replace a value in a text value
def replace(file_path, pattern, subst):
    fh, abs_path = mkstemp()
    with fdopen(fh,'w') as new_file:
        with open(file_path) as old_file:
            for line in old_file:
                new_file.write(line.replace(pattern, subst))
    copymode(file_path, abs_path)
    remove(file_path)
    move(abs_path, file_path)


#Detect mouse click
def on_click(x, y, button, pressed):
    if pressed:
        global countR, countL, count
        print("Mouse clicked at ({0}, {1}) with {2}".format(x, y, button))
        if button == Button.right:
            countR += 1
            print(countR)
            replace('mouse.txt', "Number of right clicks: " + str(countR-1), "Number of right clicks: " + str(countR))
        if button == Button.left:
            countL += 1
            print(countL)
            count += 1
            replace('mouse.txt', "Number of left clicks: " + str(countL-1), "Number of left clicks: " + str(countL))


#Collect events
with Listener(
    on_move = on_move,
    on_click = on_click,
    on_scroll = on_scroll) as listener:
    listener.join()
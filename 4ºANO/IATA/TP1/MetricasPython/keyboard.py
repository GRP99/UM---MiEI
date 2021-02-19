from pynput import keyboard
from datetime import datetime
from threading import Thread
import time


mylist = []
count = 0
bspace = 0


#Count the number of keys pressed every 60s
class counter(Thread):
    def __init__(self):
        Thread.__init__(self)
    def run(self):
        global count
        while True:
            time.sleep(60)
            f = open('fileCP.txt', 'a')
            now = datetime.now()
            current_time = now.strftime("%H:%M:%S")
            f.write(current_time + " It was pressed " + str(count) + " characters" + "\n")
            f.close()
            count = 0

t = counter()
t.start()


#Detect key press
def on_press(key):
    try:
        f = open('fileCP.txt', 'a')
        print("Key {0} pressed".format(key))
        if key != keyboard.Key.esc:
            mylist.append(format(key))
        upper = str(key.char).upper()
        f.write("-> " + upper + "\n")
        global count
        count += 1
        f.close()

    except AttributeError:
        f = open('fileCP.txt','a')
        print("Special key {0} pressed".format(key))
        upper = str(key).upper()
        f.write("-> " + upper + "\n")
        f.close()


#Detect key release
def on_release(key):
    print("Key {0} released".format(key))
    global bspace
    if key == keyboard.Key.esc:
        print(mylist)
        for x in set(mylist):
            print("Key {0} was clicked {1} time(s)".format(x, mylist.count(x)))
            f = open('fileCP.txt', 'a')
            upper = str(x).upper()
            v = str(mylist.count(x))
            if upper == 'KEY.BACKSPACE':
                bspace = mylist.count(x)
            f.write("Number of times that key" + " " + upper + " " + "was pressed" + ": " + v + "\n")
            f.close()
        f = open('fileCP.txt', 'a')
        l = str(len(mylist))
        avg = str(float(bspace)/len(mylist))
        f.write("Total number of pressed character" + ": " + l + "\n")
        f.write("Number of times that backspace was pressed/total number of pressed keys" + ": " + avg + "\n")
        f.close()
        return False


#Collect events
with keyboard.Listener(
        on_press = on_press,
        on_release = on_release) as listener:
        listener.join()

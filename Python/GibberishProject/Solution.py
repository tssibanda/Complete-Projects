#######################################################################################
# Program Description: Program that converts a line of text from a file into gibberish
# and saves it in a new file.
# Author: Thamsanqa Sibanda
# Date: 11/10/2019
# Compiler: PyCharm Python 3.x
# Text Editor: PyCharm
######################################################################################
import string

# function to translate words to gibberish
def translate(gibberish):
    # Variables and assignments
    str = gibberish
    step1=0
    gibWord = ""
    vows = "aeiouAEIOU"

    # translate word into gibberish
    for ltr in str:
        if ltr in vows:
            if step1 == 0:
                i = len(input1)
                if input1[0] == '*':
                    step1 += 1
                    input4 = ltr + input1[1:i]
                    gibWord = gibWord + input4
                    gibWord = gibWord + ltr
                else:
                    step1 += 1
                    gibWord = gibWord + input1
                    gibWord = gibWord + ltr
            else:
                i = len(input2)
                if input2[0] == '*':
                    input3 = ltr + input2[1:i]
                    gibWord = gibWord + input3
                    gibWord = gibWord + ltr
                else:
                    gibWord = gibWord + input2
                    gibWord = gibWord + ltr
        else:
            gibWord = gibWord + ltr
    return gibWord


# Main body
try:
    # open the file
    origFiletxt = open("inputFile.txt", "r")
    gibFile = open("inputFileGibberish.txt", "w")
    new_string = ""


    # prompt user for first syllable and perfom validation, i.e only letters and wildcard '*' allowed
    chkr2 = 0
    valid = 0
    while valid == 0:
        comp = 0
        if chkr2 == 0:
            input1 = input("\nEnter your first Gibberish syllable (add * for the vowel substitute): ")
            chkr2 = 1
        else:
            input1 = input("\nInvalid input!\nSyllable must only contain letters or a wildcard ('*'): ")

        # Validate input to check if a syllables contains letters or "*"
        for ltr in input1:
            if ltr == "*":
                comp += 1
            if ltr in string.ascii_letters:
                comp += 1
        if comp == len(input1):
            valid = 1
        else:
            valid = 0


    # prompt user for second syllable and perfom validation, i.e only letters and wildcard '*' allowed
    chkr2 = 0
    valid = 0
    while valid == 0:
        comp = 0
        if chkr2 == 0:
            input2 = input("\nEnter your second Gibberish syllable (add * for the vowel substitute): ")
            chkr2 = 1
        else:
            input2 = input("\nInvalid input!\nSyllable must only contain letters or a wildcard ('*'): ")

        # Validate input to check if a syllables contains letters or "*"
        for ltr in input2:
            if ltr == "*":
                comp += 1
            if ltr in string.ascii_letters:
                comp += 1
        if comp == len(input2):
            valid = 1
        else:
            valid = 0

    # Translate the file
    for line in origFiletxt:
        str = line
        #counter to indicate if first word is being translated
        c = 0

        for word in str.split():
            k=len(word)
            nopunc = ""

            #check if first word
            if c == 0:
                c +=1
                # check if first word ends with punctuation
                if word[k-1] in string.punctuation:
                    nopunc = word[k-1]
                    newWord = word[0:k-1]
                    transWord = translate(newWord)
                    new_string = new_string + transWord + nopunc
                else:
                    newWord = word
                    transWord = translate(newWord)
                    new_string = new_string + transWord + nopunc
            else:
                if word[k-1] in string.punctuation:
                    nopunc = word[k-1]
                    newWord = word[0:k-1]
                    transWord = translate(newWord)
                    new_string = new_string + " " + transWord + nopunc
                else:
                    newWord = word
                    transWord = translate(newWord)
                    new_string = new_string + " " + transWord

    gibFile.write(new_string)
except IOError:
    print("Error: no such file or can’t write")
else:
    print("Written to file successfully")

origFiletxt.close()
gibFile.close()
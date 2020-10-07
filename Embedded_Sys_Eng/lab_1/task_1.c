#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include "ctype.h"
#include "tree.c"
// Read file contents

// Store all identifiers (tokens) strings that start with a letter or an underscore and continue with letters and nums

// Sort all these identifiers and output them
FILE * file;

int main() {

    // Open the file, returns null if the file wasn't opened
    file= fopen("../t.txt", "r");
    char buff[255];

    // Loop through our file getting lines
    while(fgets(buff, 255, file)){
        char *line;
        line = buff;

        // Loop through the lines getting each character
        int pos = 0;
        while (*line){
            char word[255];
            char c;
            c = *line;

            // If the word is empty check the character is alphabetic or an underscore it's a valid starting character
            if (word[0] == '\0') {
                if (isalpha(c) || c == '_') {
                    word[0] = c;
                    pos++;
                }
            }
            // Else if we have a char in the word check if its a letter, underscore or number
            else{
                if (isalpha(c) || c == '_' || isalnum(c)) {
                    word[pos] = c;
                    pos++;
                }
                else{
                    word[pos] = '\0';
                    printf("Added: %.*s\n", pos, word + 0);
                    head = insert(head, word);
                    word[0] = '\0';
                    pos = 0;
                }
            }
            line++;
        }

    }
    printf("\nOur list in order:\n");
    inorder(head);

//    if (file != NULL) {
//        printf("Ok");
//
//    }
//    else{
//        printf("ERROR: The file does not exist...");
//        exit(1);
//    }
    return 0;
}

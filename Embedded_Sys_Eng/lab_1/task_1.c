#include <stdio.h>
#include "ctype.h"
#include "tree.c"

FILE * file;

int main() {

    // Open the file, returns null if the file wasn't opened
    file= fopen("../t.txt", "r");
    if (file != NULL) {
        char buff[255];

        // Loop through our file getting lines
        while (fgets(buff, 255, file)) {
            char *line;
            line = buff;

            // Loop through the lines getting each character
            int pos = 0;
            while (*line) {
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
                else {
                    if (isalpha(c) || c == '_' || isalnum(c)) {
                        word[pos] = c;
                        pos++;
                    } else {
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
        //Order and print our ordered list
        printf("\nOur list in order:\n");
        inorder(head);
    }

    // If the file was not found print an error message
    else{
        printf("ERROR: The file t.txt could not be found...");
        exit(1);
    }
    return 0;
}

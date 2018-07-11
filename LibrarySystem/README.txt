Library System Program

THINGS TO NOTE: All of the source codes need to be cleaned up.
This project was created in eclipse. Easiest way to run this 
project is to import it to eclipse and run it.
For the program to run without any erros, all of the provided 
files must be present.  When the program is ran for the first time, the user 
must first borrow some documents before returning them. 
After having done the above, if the user enters every information exactly 
as instructed to, the program works with no errors. 
Please read the program description section to check how the program works
and the program bugs section to be aware of all the bugs the program has.



Program Description
The main functionality of this program is to be able to borrow
and return documents at a library. Different users can do more 
than just borrwing and returning documents such as librarians. 
More features to be added later such as reserving documents, 
extending the loan period, notify the user when a document
is overdue when user logs in in the system, and other features.

1. When the program starts, it first prompts the user to enter a name 
and a password. Names and passwords are stored in "logins.txt" in
the format Name;password separated by a comma. "UserFile.txt" contains
all of the names of the users and the corresponding type of user.
Once the user enters the correct name and password. It logs in.

2. After logging in, two choices are given to the user, borrow document or 
return document. If a librarian is logged in, 4 choices will be given 
to the user, borrow, return, add, and remove document. 
Books and journals are stored in "bookfile.txt" and "journalfile.txt" files.

3. When borrowing/returning documents, it will ask the user to choose
what document they wish to borrow/return, then search for the book/journal
they wish to borrow/return. User can enter any character to search but it is
recommended for the user to enter the title of the document they wish to
borrow/return in order to borrow/return the desired book.

4. When user borrows/returns a book, the transaction gets logged in 
"logs.txt" file. A transaction file "transaction.txt" is created/updated 
that stores the current documents that are borrowed by users. When a user returns 
a book/journal, the returned book/bournal gets deleted from "transaction.txt"
file. 

5. If a librarian logs in and decides to add/remove a docoument, it asks
what document they wish to add/remove. After choosing a document and entering 
the right document information to add/remove, the book/journal gets 
added/deleted from the "bookfile.txt"/"journalfile.txt" databases.



Program Bugs
1. If the user wants to return a book or a journal, but the user
hasn't borrowed any documens, the program crashes because the 
"transaction.txt" file is not found. 

2. When the user is returning a document and a space is entered instead of the 
title of the document to search, both books and journals are displayed 
to remove instead of the specific document specified by the user. 

3. When the user is returning a book and the user enters a space to search
the book, books and journals are displayed to be returned. If the user
decides to return a journal while returning a book, the journal gets deleted 
from the "transaction.txt" file, transaction is logged in "logs.txt", and 
the "journalfile.txt" doesn't get updated with the returned journal. 

4. When the user is returning a journal and the user enters a space to search
the journal, books and journals are displayed to be returned. If the user
decides to return a book while returning a journal, the title of the book
gets recorded in the "journalfile.txt" file, the "bookfile.txt" is not 
updated, the transaction is logged in "logs.txt" file and the "transaction.txt"
gets updated. 

5. When the user is prompted to enter 0 or any other number to continue
running the program, if letters are intered instead of numbers, the program crashes. 

6. If letters are entered when the user is prompted to choose the document 
to borrow/return, journal document is chosen automatically. 

7. Users can only borrow one journal. When journals are borrowed, the whole
document will be deleted from "journalfile.txt", and recorded in
"transaction.txt". 

8. Users can borrow the same book multiple times instead of being restricted
to borrow only 1 copy of a book. 

9. When a librarian removes a book, all of the copies are removed as well
instead of just 1 copy

10. When a space is entered to search for a book/journal to remove, 
all of the books or journals get removed. 


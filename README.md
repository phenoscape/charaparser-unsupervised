charaparser-unsupervised
========================

Java implementation of unsupervised CharaParser code

* Read Treatment
    * Sentence segmentation
        * Handle abbreviation. 

First replace all dot marks after abbreviation by "[DOT]". After sentence segmentation, restore them.

The regular expression like "blv?d" would match both "blvd" and "bld". The question mark makes the previous letter optional.

* Manage Data Holder
** Update Table

For each word, if it is not in the SingularPlural Table, if it is a noun in singular form, find its plural form, and add the (singular, plural) pair into the SingularPlural Table.

***　Discount POS
    Given a word, its old POS, its new POS, and the mode,
    1. Find the flag of the word in Unknownword holder, then select all words from Unknownword table who have the same flag including itself
    1. For each of them, 
        1.1. If from WordPOS holder its certaintyU is less than 1, AND mode is "all"
		    1.1.1. Delete the entry from WordPOS holder
		    1.1.1. Update Unknownword holder
		    1.1.1. If the POS is "s" or "p", delete all entries contains word from Singularplural holder as well
        1.1. Else insert (nword, Oldpos, newpos) into discounted table

*　Add Heuristics Nouns　        
** Learn Heuristics Nouns

For each noun, 

** Character Heuristics
        
        
* discover
** with parameter "start"
At the every beginning, only those sentence whose first word is a p, could have a tag of "start", see populateSentece section.

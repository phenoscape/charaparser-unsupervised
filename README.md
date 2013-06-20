charaparser-unsupervised
========================

Java implementation of unsupervised CharaParser code

* Read Treatment
    * Preprocess
        * First replace all dot marks after abbreviation by "[DOT]". After sentence segmentation, restore them.
    * Sentence segmentation
        * Handle abbreviation. 
    * Get Lead of Sentence
    In most cases, the lead of a sentence is just the first NUM_LEAD_WORDS words in the sentence, where NUM_LEAD_WORDS has been pre-define as 3. However, there are two expections:
    1) When there is any :;.[(] in the sentence, only the words before any ,:;\.\[(] are counted. For example, for the sentence
        word1 word2 . word3
    only
        word1 word2
    are kept.
    2) Whene there is any preposition word in the sentence, only the words before the preposition word and the preposition word are counted. For example, for the sentence
        lepidotrichia of fin webs
    only the word before the preposition word "of" and the preposition word "of"
        lepidotrichia of
    are kept.
    
* Get All Unique Words




The regular expression like "blv?d" would match both "blvd" and "bld". The question mark makes the previous letter optional.


*　Add Heuristics Nouns　        
** Learn Heuristics Nouns

For each noun, 

** Character Heuristics

* Add Stop Words

* Add Characters

* Add Numbers

* Add Cluster Strings

* Add Proper Nouns

* Learn POS By Suffix

* Markup Sentence By Pattern

* Markup Ignore
    This one is not used here.
    
* Learning rules with high certainty
    Discover with parameter "start"
    
* discover
** with parameter "start"
At the every beginning, only those sentence whose first word is a p, could have a tag of "start", see populateSentece section.

* Bootstrapping rules
    Discover with parameter "normal"    
        


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

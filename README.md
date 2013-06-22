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


* Add Heuristics Nouns
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
	with parameter "start"
	
	For each sentence whose status is "start" and tag is not "ignore" and "null", 

	At the every beginning, only those sentence whose first word is a p, could have a tag of "start", see populateSentece section.
	
	* Build pattern from the lead words of the sentence
	* Find those sentences which match to this pattern
		For each of those sentences,
		* do rule based learn, all the way to the point where no further new knowledge can be learned. Then stop.

* discover
	Bootstrapping rules
	Discover with parameter "normal"  
	Same to the previous step, except that the parameter is "normal" instead of "start".  
        


* Manage Data Holder
** Update Table
If the pos is noun, find whether its is a singular or plural.

*** Mark Known Word

**** Precess New Word

Update Unknown Word
Put (word, tag) into UknownWord holder

***** the holder is WordPOS
****** update POS
case 1: the word does not exist, add it. Increase the count by 1.
case 2: the word already exists, update it
    2.1 the old POS is NOT same as the new POS, AND	the old POS is b or the new POS is b
    ??? new POS wins
    change POS. Increase the count by the number of changes made.
    ??? old POS wins
    update the word in WordPOS holder
    2.2 the old POS and the new POS are all [n]
    update role and certaintyU

***** the holder is modifier, add to Modifier holder


Since we know the word, we try to learn new words from this word based on its prefix, by forming some new words and checking if they exist in UnknownWord holder.

Increase the count by 1 whenever a new word has been learned.

For each word, if it is not in the SingularPlural Table, if it is a singular and not in the SingularPlural holder, find its plural form and add the (singular, plural) pair into the SingularPlural holder. Similarly, if it is a plural and not in the SingularPlural holder, find its singular form and add the (singular, plural) pair into the SingularPlural holder.
*** Discount POS
    Given a word, its old POS, its new POS, and the mode,
    1. Find the flag of the word in Unknownword holder, then select all words from Unknownword table who have the same flag including itself
    1. For each of them, 
        1.1. If from WordPOS holder its certaintyU is less than 1, AND mode is "all"
		    1.1.1. Delete the entry from WordPOS holder
		    1.1.1. Update Unknownword holder
		    1.1.1. If the POS is "s" or "p", delete all entries contains word from Singularplural holder as well
        1.1. Else insert (nword, Oldpos, newpos) into discounted table

Apart the updates on the holders themselves, in this step the count of how many updates have been made in this step is returned.
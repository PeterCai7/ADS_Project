# ADS_Project
---
This is a course project of COP5536-Advanced Data Structure instructed by Dr. Sahni.

---

- Implement a system to find the n most popular hashtags that appear on social media such as Facebook or Twitter. 
  For the scope of this project, hashtags will be given from an input file.
  Basic idea for the implementation is to use a max priority structure to find out the most popular hashtags.
- Use the following structures for the implementation.
  1. Max Fibonacci heap: use to keep track of the frequencies of hashtags.
  2. Hash table: The key for the hash table is the hashtag, and the value is the pointer to the corresponding node in the Fibonacci heap.

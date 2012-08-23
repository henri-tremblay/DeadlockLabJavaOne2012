The purpose of this lab is to solve another simple deadly embrace style of
deadlock.  But this time, instead of defining a global order, we use tryLock()
with owned locks.

Our second lab is similar to the first, except that instead of ordering the
locks, we now use the ReentrantLock.tryLock() method.  The aim is again to
avoid the deadlock.  This time the deadlock is between threads calling the
Lock.lock() method, rather than synchronized.

1. Run the code and verify that you see a deadlock by capturing a stack trace.
   Depending on your machine, you might need a few runs to see the issue.

2. Once you have discovered the deadlock, verify that it involves the left
   and right locks.

3. Change the Thinker class to use tryLock() instead of lock() to avoid the
   deadlock situation.  One solution is to call left.lock() and right.tryLock()
   and then to left if you are not successful in locking the right.  Another is
   to use tryLock() with both.

4. Verify that the deadlock has now disappeared.

5. As an extra, you can count how many times you had to retry locking.  This
   might help you to decide whether to use left.lock() or left.tryLock().  You
   can also try adding a random sleep to reduce the number of retries, but keep
   in mind that your overall time taken to run the test might increase.

Good luck!  You have 15 minutes to solve this lab.
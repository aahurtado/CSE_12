NAME: Aaron Hurtado
ID: A99128987
LOGIN: cs12scz

testAdd()
Test if the add method in the MyLinkedList class adds to the end of the list
by adding a value to the list and checking if that value is indeed the last
value in the list.

testAddException()
Test if addding a null pointer to the list causes a NullPointerException.

testAddException2()
Test if adding to the list at index -1 will cause an IndexOutOfBoundsException

testAddException3()
Test if adding to the list at idnex 100 will cause an IndexOutOfBoundsException

testGet()
Test if get method returns correct values.

testSetException()
Test if setting an element in the list to null would cause a NullPointerException

testSetException2()
Test if setting an element at index -1 would cause an IndexOutOfBoundsException

testRemove()
Test if removing from the beginning of the list, middle of the list, and
end of the list correctly works.

testClear()
Test if clear method works by using the clear method on the several list and
seeing if it is empty.

testNext()
Test if next() in MyListIterator returns the correct value.

testHasPrevious()
Test if the hasPrevious() method works correctly by having cursor at intitial
location then checking hasPrevious() (should be false). Then moving the
iterator foward by one then checking hasPrevious() again (now should be true).

testIteratorException()
Test if previous method will cause a NoSuchElementException when called while
the cursor is to the right of the head node.

testNextIndex()
Test if nextIndex() method returns correct value and returns the list size
when at the end of the list.

testPreviousIndex()
Test if previousIndex() method returns correct value and returns -1 when at
the start of the list.

testPrevious()
Test if add() and remove() work by adding an element then checking if it was
added followed by removing the element and checking to make sure it was
deleted.

testSetValue()
Test if set returns the element previously at the position being set.

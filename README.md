# Heap Simulator

## _This simulator demonstrates Heap memory segment basic operations._

### Features

- The simulator allows the user to choose a size (in words) of the Heap which he wants to simulate.
  <br>
- Than the user writes which command he would like to perform:
  <br>
  - To perform malloc the user should specify - "malloc [size]" where size is the memory block size (in words) that he wants to allocate.
  - To perform free the user should specify - "free [addr]" where addr is the memory block addresse that he wants to free. If there is no memory block with such addresse, nohing happens.
  - To perform defrag the user should specify - "defrag".

## Running the program

Running from command:

```sh
[java Main ][option]
```

option - specify the rate of the animation. when no argument is given in the option section the rate is set to default which is 2000.

For exmple:

```sh
java Main 1500
```

```sh
java Main
```

## Animation

- Each memory block is of the format [ addr | length ] where addr is the base address of the block and length is its size in words.

![Simulator Screenshot](./archive/Sim%20screenshot.png)

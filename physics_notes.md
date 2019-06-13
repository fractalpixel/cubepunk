# Physics Notes

## Alternatives:

### Ode4j
* A pain to setup & use
* Appears to have inconsistencies / bugs
* No documentation

### JBullet
* Last updated 2010
* No documentation

### JME JBullet
* Tightly coupled to JME
* Native integration?

### Roll my own
* -- Lots of work
* + Can take suitable shortcuts, only implement needed things
* + Learn physics engine building
* + Can support special cases

### No physics
* Just fudge what is needed
* This!
* Evolve into more complete physics engine as needed.


## Requirements

* Blocks that move in different ways, connected with joints
* Block <-> ground collisions
* block <-> block collisions, probably enough if just checking for collisions with other block group.




# Architecture

* Need to step simulation with fixed time step, variable time step doesn't work for physics..  Up to max steps if lagging behind.

## Collision detection
* Detect penetrations
* Create temporary joints for penetrations? (use flyweight pattern)
* Clear temporary joints after update

## Applying constraints / joints
* Iterate:
  * resolve each constraint a bit?

## Physics body simulation
* Apply forces to velocities
 * apply velocities to position
* Ruger kutta (?) instead of euler? - change later maybe?
* Rotations & rotational forces & rotational inertia stuff for different body shapes
  * assume everything is a sphere...?

## Surrounding medium
* Air / water resistance / drag
  * Some simple drag calculations (later when making water moving robot swimmers one could look at better fluid friction)
* Buoyancy


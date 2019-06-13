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

## Collision detection
* Detect penetrations
* Create temporary joints for penetrations
* Clear temporary joints after update

## Applying constraints / joints
* Iterate:
  * resolve each constraint a bit

## Physics body simulation
* Apply forces to velocities
 * apply velocities to position

## Surrounding medium
* Air / water resistance / drag
* Buoyancy


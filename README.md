# MiniML
A lightweight computation graph library, primarily intended for machine learning, written in pure Kotlin.

## Example
```kotlin
val a = placeholder(scalarOf(20.5))
val b = variable(scalarOf(10.0))
val c = variable(scalarOf(98.1))
val d = variable(scalarOf(-20.2))
val output = ((a * c.square()) - (a * b)) + d

println("Forwardpass: ${output.forward()}")
output.backward()
println("Gradient of c w.r.t output: ${c.gradient}")
```

# Performance enumeration

Performance is Java enum that contains constants:
- MIN
- CLOSETOMIN
- HALF
- CLOSETOMAX
- MAX

The constant value affects the number of threads in the pool.<br>
The greater the value of the constant, the greater the number of threads.<br>

The number of threads is determined by using an expression:
````java
int N_CPUs = Runtime.getRuntime().availableProcessors();
int CPU = 1;
if (N_CPUs != 1)
{
    switch (Speed)
    {
        case MAX: CPU = (N_CPUs > 1) ? (N_CPUs) : (2); break;
        case HALF: CPU = (N_CPUs / 2 > 1) ? (N_CPUs / 2) : (2); break;
        case CLOSETOMAX: CPU = (N_CPUs * 3 / 4 > 1) ? (N_CPUs * 3 / 4) : (2); break;
        case CLOSETOMIN: CPU = (N_CPUs / 4 > 1) ? (N_CPUs / 4) : (2); break;
        default: CPU = 1; break;
    }
}
````
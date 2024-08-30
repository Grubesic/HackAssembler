// C_PUSH constant 111
@111
D=A
@SP
A=M
M=D
@SP
M=M+1

// C_PUSH constant 333
@333
D=A
@SP
A=M
M=D
@SP
M=M+1

// C_PUSH constant 888
@888
D=A
@SP
A=M
M=D
@SP
M=M+1

// C_POP static 8
@SP
M=M-1
A=M
D=M
@StaticTest.asm.8
M=D

// C_POP static 3
@SP
M=M-1
A=M
D=M
@StaticTest.asm.3
M=D

// C_POP static 1
@SP
M=M-1
A=M
D=M
@StaticTest.asm.1
M=D

// C_PUSH static 3
@StaticTest.asm.3
D=M
@SP
A=M
M=D
@SP
M=M+1

// C_PUSH static 1
@StaticTest.asm.1
D=M
@SP
A=M
M=D
@SP
M=M+1

// C_ARITHMETIC sub
@SP
M=M-1
A=M
D=M
A=A-1
M=M-D

// C_PUSH static 8
@StaticTest.asm.8
D=M
@SP
A=M
M=D
@SP
M=M+1

// C_ARITHMETIC add
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M


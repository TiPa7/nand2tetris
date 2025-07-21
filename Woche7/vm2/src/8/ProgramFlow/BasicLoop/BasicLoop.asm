@0
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 0
@0
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 0
(LOOP) //label LOOP
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@LCL
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 0
@SP
AM=M-1
D=M
A=A-1
M=M+D //add
@0
D=A
@LCL
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop local 0
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@1
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@0
D=A
@ARG
D=D+M
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop argument 0
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@SP
AM=M-1
D=M
@LOOP
D;JNE //if-goto LOOP
@LCL
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push local 0
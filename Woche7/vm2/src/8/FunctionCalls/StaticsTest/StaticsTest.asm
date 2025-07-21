@256
D=A
@SP
M=D //SP=256
@602d5010-587b-49da-a8fe-1a4e34592f53
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@5
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Sys.init
0;JMP //goto g (ending call g nargs)
(602d5010-587b-49da-a8fe-1a4e34592f53)
(Class1.set) //function Class1.set 0
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@Class1.0
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 0
@ARG
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 1
@Class1.1
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 1
@0
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 0
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return
(Class1.get) //function Class1.get 0
@Class1.0
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 0
@Class1.1
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return
(Class2.set) //function Class2.set 0
@ARG
D=M
@0
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 0
@Class2.0
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 0
@ARG
D=M
@1
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D //push argument 1
@Class2.1
D=A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop static 1
@0
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 0
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return
(Class2.get) //function Class2.get 0
@Class2.0
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 0
@Class2.1
D=M
@SP
A=M+1
A=A-1
M=D
@SP
AM=M+1
A=A-1
M=D //push static 1
@SP
AM=M-1
D=M
A=A-1
M=M-D //sub
@LCL
D=M
@R13
M=D //frame=LCL
@5
A=D-A
D=M
@R14
M=D //retrAddr = *(frame-5)
@SP
AM=M-1
D=M
@ARG
A=M
M=D //*ARG = pop
@ARG
D=M+1
@SP
M=D //SP=ARG+1
@R13
AM=M-1
D=M
@THAT
M=D //THAT=*(frame-1)
@R13
AM=M-1
D=M
@THIS
M=D //THIS=*(frame-2)
@R13
AM=M-1
D=M
@ARG
M=D //ARG=*(frame-3)
@R13
AM=M-1
D=M
@LCL
M=D //LCL=*(frame-4)
@R14
A=M
0;JMP //goto retrAddr //return
(Sys.init) //function Sys.init 0
@6
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 6
@8
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 8
@30bce71e-59a9-4974-8a04-693a5ff34fee
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@7
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Class1.set
0;JMP //goto g (ending call g nargs)
(30bce71e-59a9-4974-8a04-693a5ff34fee) //call Class1.set 2
@0
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop temp 0
@23
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 23
@15
D=A
@SP
AM=M+1
A=A-1
M=D //push constant 15
@08bff15f-8bf7-4ca0-bcdc-19a546eea1c5
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@7
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Class2.set
0;JMP //goto g (ending call g nargs)
(08bff15f-8bf7-4ca0-bcdc-19a546eea1c5) //call Class2.set 2
@0
D=A
@5
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D //pop temp 0
@1cd4602a-4e87-49ab-ab5a-de3b384b25e3
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@5
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Class1.get
0;JMP //goto g (ending call g nargs)
(1cd4602a-4e87-49ab-ab5a-de3b384b25e3) //call Class1.get 0
@ce5b28d7-c1d1-48fa-83d2-0ef46047dbcb
D=A
@SP
AM=M+1
A=A-1
M=D //push returnAddress (starting call g nargs)
@LCL
D=M
@SP
AM=M+1
A=A-1
M=D //push LCL
@ARG
D=M
@SP
AM=M+1
A=A-1
M=D //push ARG
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D //push THIS
@THAT
D=M
@SP
AM=M+1
A=A-1
M=D //push THAT
@SP
D=M
@5
D=D-A
@ARG
M=D //ARG = SP-nArgs-5
@SP
D=M
@LCL
M=D //LCL=SP
@Class2.get
0;JMP //goto g (ending call g nargs)
(ce5b28d7-c1d1-48fa-83d2-0ef46047dbcb) //call Class2.get 0
(END) //label END
@END
0;JMP //goto END
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker

fun main(args: Array<String>) {
    val text = """
public class HelloWorld {

    public static void main(String[] args) {
         System.out.println("Hello, World", 1);
         System.out.add(1, 3);
    }
}
    """
    val lexer = Java8Lexer(CharStreams.fromString(text))
    val tokenStream = CommonTokenStream(lexer)
    val parser = Java8Parser(tokenStream)
    val compilationUnit = parser.compilationUnit()
//    compilationUnit.typeDeclaration().forEach { context ->
//        println("context: $context")
//    }
    val walker = ParseTreeWalker()
    walker.walk(object: Java8BaseListener() {
        override fun enterMethodInvocation(ctx: Java8Parser.MethodInvocationContext?) {
            println("expressionName: ${ctx?.expressionName()?.Identifier()}")
            println("methodName: ${ctx?.methodName()?.Identifier()}")
            println("typeName: ${ctx?.typeName()?.Identifier()}")
            println("identifier: ${ctx?.Identifier()}")
            println("no of children: ${ctx?.argumentList()?.childCount}")
//            ctx?.argumentList()?.children?.forEach {
//                println("child: ${it.text}")
//            }

            ctx?.argumentList()?.expression()?.forEach {
                println("expression: ${it?.text}")
            }

            println("expressions size: ${ctx?.argumentList()?.expression()?.size}")
        }
    }, compilationUnit)
}
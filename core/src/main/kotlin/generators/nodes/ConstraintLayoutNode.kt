package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.joinToCode
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ConstraintLayoutNode(
    override val children: Iterable<ViewNode>,
    private val info: Info,
    private val imports: Imports
) : ViewNode {
    override val id: String
        get() = info.id

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = imports.viewImports.constraintLayout.root
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = imports.attributeImports.modifier,
            ChainedMemberName(
                prefix = imports.attributeImports.background,
                info.backgroundColor.argument()
            ),
            ChainedMemberName(
                prefix = info.width.prefix(),
                info.width.argument(),
                containsArguments = true
            ),
            ChainedMemberName(
                prefix = info.height.prefix(),
                info.height.argument(),
                containsArguments = true
            )

        ).codeBlock()
        if (modifiers.isNotEmpty()) paramCodeBlocks.add(modifiers)

        return CodeBlock.builder()
            .add("%T (%L)", instance, paramCodeBlocks.joinToCode())
            .add(
                CodeBlock.builder()
                    .beginControlFlow(" {")
                    .add(createConstraintRefs())
                    .add(childrenToCodeBlock())
                    .endControlFlow()
                    .build()
            )
            .build()
    }

    private fun createConstraintRefs(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.addStatement(
                "val ${it.id.replaceFirstChar { first -> first.lowercase() }}Ref = %M(Any())",
                imports.viewImports.constraintLayout.constrainedLayoutReference
            )
        }
        return codeBlock.build()
    }

    override fun imports(): Iterable<ClassName> {
        return children.map { it.imports() }.flatten()
    }

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    data class Info(
        val id: String,
        val backgroundColor: ColorAttribute,
        val width: LayoutWidth,
        val height: LayoutHeight
    )

}

package generators.nodes

import ServiceLocator
import com.squareup.kotlinpoet.*
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.constraints.Constraints
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class TextViewNode(
    private val info: Info,
    private val imports: Imports
) : ViewNode {


    override val children: Iterable<ViewNode> = emptyList()
    override val id: String
        get() = info.id

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = imports.viewImports.text
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of(""""${info.text}""""))

        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = imports.attributeImports.modifier,
            ChainedMemberName(
                prefix = imports.attributeImports.background,
                info.backgroundColor.argument()
            ),
            ChainedMemberName(
                prefix = MemberName("", "weight"),
                CodeBlock.of(if (info.weight >= 0F) "${info.weight}F" else "")
            ),
            ChainedMemberName(
                prefix = info.width.prefix(),
                info.width.argument(),
                containsArguments = info.width.containsArguments()
            ),
            ChainedMemberName(
                prefix = info.height.prefix(),
                info.height.argument(),
                containsArguments = info.height.containsArguments()
            ),
            ChainedMemberName(
                info.constraints.memberNamePrefix,
                info.constraints.codeBlock()
            )
        ).codeBlock()
        if (modifiers.isNotEmpty())
            paramCodeBlocks.add(modifiers)
        if (info.textColor.isEmpty().not()) {
            paramCodeBlocks.add(CodeBlock.of("color = %L", info.textColor.argument()))
        }
        if (info.fontSize > 0) {
            paramCodeBlocks.add(CodeBlock.of("fontSize = ${info.fontSize}.sp"))
        }
        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .add("\n")
            .build()
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("androidx.compose.ui.unit", "sp")
        ) + info.textColor.imports()
    }


    data class Info(
        val id: String,
        val width: LayoutWidth,
        val height: LayoutHeight,
        val text: String,
        val textColor: ColorAttribute,
        val fontSize: Int,
        val backgroundColor: ColorAttribute,
        val weight: Float,
        val constraints: Constraints
    )

}
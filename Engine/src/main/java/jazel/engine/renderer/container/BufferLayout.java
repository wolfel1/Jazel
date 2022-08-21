package jazel.engine.renderer.container;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BufferLayout {

    private List<BufferElement> elements;
    private int stride = 0;

    public BufferLayout(List<BufferElement> elements) {
        this.elements = elements;
        calculateOffsetAndStride();
    }

    private void calculateOffsetAndStride() {
        int offset = 0;
        stride = 0;

        for (var element : elements) {
            element.setOffset(offset);
            offset += element.getSize();
            stride += element.getSize();
        }
    }
}

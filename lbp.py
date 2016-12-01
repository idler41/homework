
import numpy as np
from PIL import Image

class LBP:
    def __init__(self, input, output):
        self.image = Image.open(input).convert("L")
        self.width = self.image.size[0]
        self.height = self.image.size[1]
        self.patterns = []
        self.output = output

    def execute(self):
        self._process()
        if self.output:
            self._output()

    def _process(self):
        pixels = list(self.image.getdata()) 
        pixels = [pixels[i * self.width : (i + 1) * self.width] for i in xrange(self.height)]
        for i in xrange(1, self.height - 1):
            previous_row = pixels[i - 1]
            current_row = pixels[i]
            next_row = pixels[i + 1]

            for j in xrange(1, self.width - 1):
                pixel = current_row[j]
                pattern = 0
                pattern = pattern | (1 << 0) if pixel < previous_row[j-1] else pattern
                pattern = pattern | (1 << 1) if pixel < previous_row[j] else pattern
                pattern = pattern | (1 << 2) if pixel < previous_row[j+1] else pattern
                pattern = pattern | (1 << 3) if pixel < current_row[j+1] else pattern
                pattern = pattern | (1 << 4) if pixel < next_row[j+1] else pattern
                pattern = pattern | (1 << 5) if pixel < next_row[j] else pattern
                pattern = pattern | (1 << 6) if pixel < next_row[j-1] else pattern
                pattern = pattern | (1 << 7) if pixel < current_row[j-1] else pattern
                self.patterns.append(pattern)

    def _output(self):
        result_image = Image.new(self.image.mode, (self.width - 2, self.height - 2))
        result_image.putdata(self.patterns)
        result_image.show()


image_path = "test.png"
image_ori = Image.open(image_path)
image_ori.show()
lbp = LBP(image_path,True)
lbp.execute()

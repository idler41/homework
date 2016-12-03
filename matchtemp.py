

import numpy as np
from PIL import Image
import cv2 as cv

face_image_gray = Image.open("test.png").convert("L")
sample_image_gray = Image.open("template.png").convert("L")
face_image_gray_pixels = list(face_image_gray.getdata())  
sample_image_gray_pixels = list(sample_image_gray.getdata())  

face_image_width = face_image_gray.size[0]
face_image_height = face_image_gray.size[1]

sample_image_width = sample_image_gray.size[0]
sample_image_height = sample_image_gray.size[1]


import pdb; pdb.set_trace
()
lmin = 0
x = 0
y = 0
print face_image_height, face_image_width, sample_image_height, sample_image_width

for i in xrange( 0, face_image_height - sample_image_height, 5 ):
    for j in xrange( 0, face_image_width - sample_image_width, 5 ):
        ivalue = 0
        for n in xrange( 0, sample_image_height ):
            for m in xrange( 0, sample_image_width ):
                
                aa = face_image_gray_pixels[ (i+n)*face_image_width + j+m ]
                bb = sample_image_gray_pixels[ n*sample_image_width + m ]
                ivalue += abs(aa*aa - bb*bb)

        if (i == 0 and j == 0) or (ivalue < lmin) :
            lmin = ivalue
            x = j
            y = i


print "match - ", x ,y  

# show result image
image = cv.imread('test.png')
cv.rectangle(image,(int(x),int(y)),(int(x)+sample_image_width,int(y)+sample_image_height),(255,255,255),1,0)
cv.imshow("match", image)
cv.waitKey(30000)
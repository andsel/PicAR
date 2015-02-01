package org.dna.picar

import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.springframework.web.multipart.commons.CommonsMultipartFile

class ImageService {

    static final int THUMB_HEIGHT = 320

    def storeImageIntoCard(CommonsMultipartFile sourceFile, File destinationFile, Card card) {
        log.info "Adding ${sourceFile.originalFilename} to $destinationFile for card $card"
        sourceFile.transferTo(destinationFile)

        int dotIndex = destinationFile.name.lastIndexOf('.')
        String fileName = destinationFile.name[0..dotIndex - 1]
        String extension = destinationFile.name[dotIndex+1..-1]

        //create thumbnail
        BufferedImage original = ImageIO.read(destinationFile)

        double ratio = THUMB_HEIGHT / original.getHeight(null)
        int thumb_witdh = original.getWidth(null) * ratio

        BufferedImage img = new BufferedImage(thumb_witdh, THUMB_HEIGHT, BufferedImage.TYPE_INT_RGB)
        img.createGraphics().drawImage(original.getScaledInstance(thumb_witdh, THUMB_HEIGHT, Image.SCALE_FAST), 0,0,null)
        ImageIO.write(img, "png", new File(destinationFile.parent, "${fileName}_thumb.png"))

        //store data in the card
        StoredImage image = new StoredImage(fileName: fileName, extension: extension)
        card.addToImages(image)
        card.save(failOnError: true)
    }


    /**
     * Remove from disk the original image and the thumbnail, then removes also the reference in the DB.
     * Return a string with the message code for error if any, null else.
     * */
    String deleteImage(StoredImage image, File destinationDir) {
        //check not collision in filesystem
        File thumbFile = new File(destinationDir, "${image.fileName}_thumb.png")
        boolean deleted = thumbFile.delete()
        if (!deleted) {
            log.error "Can't remove file $thumbFile from disk"
            return 'card.image.thumb.not.delete'
        }

        File originalFile = new File(destinationDir, "${image.fileName}.${image.extension}")
        deleted = originalFile.delete()
        if (!deleted) {
            log.error "Can't remove file $originalFile from disk"
            return 'card.image.original.not.delete'
        }

        Card card = image.card
        card.removeFromImages(image)
        image.delete()
    }
}

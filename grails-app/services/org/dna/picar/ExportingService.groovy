package org.dna.picar

import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Phrase
import com.itextpdf.text.Chunk
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.Element
import com.itextpdf.text.BaseColor
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.Utilities

/**
 * Used to generate the pdf exports
 */
class ExportingService {

    private static final Font BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)
    private static final int PHOTO_WIDTH = 60 //mm

    def messageSource

    ByteArrayOutputStream generateCatalogDoc(File destinationDir, Locale locale) {
        try {
            def document = new Document()
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
            PdfWriter.getInstance(document, baos)
            document.open()
            PdfPTable table = new PdfPTable(2)
            table.setTotalWidth([402 - Utilities.millimetersToPoints(PHOTO_WIDTH), Utilities.millimetersToPoints(PHOTO_WIDTH)] as float[])
            for (Card card : Card.list([sort: 'inventoryNumber', order: 'asc'])) {
                PdfPCell descriptionCell = new PdfPCell()
                Paragraph signatureParagraph = new Paragraph()
                signatureParagraph.add(new Chunk('Segnatura: ', BOLD))
                signatureParagraph.add(new Chunk(card.signature))
                signatureParagraph.add(new Chunk('Cronologia: ', BOLD))
                signatureParagraph.add(new Chunk(card.dating ?: ''))
                descriptionCell.addElement(signatureParagraph)

                Paragraph subjectParagraph = new Paragraph()
                subjectParagraph.add(new Chunk('Titolo: ', BOLD))
                subjectParagraph.add(new Chunk(card.title ?: ''))
                descriptionCell.addElement(subjectParagraph)

//                Paragraph localizationParagraph = new Paragraph()
//                localizationParagraph.add(new Chunk('Localizzazione :', BOLD))
//                localizationParagraph.add(new Chunk(card.localization ?: ''))
//                descriptionCell.addElement(localizationParagraph)

                table.addCell(descriptionCell)

                //add the image part
                if (!card.images.empty) {
                    float borderWidth = 2
                    File thumbFile = new File(destinationDir, "${card.images[0].fileName}_thumb.png")
                    Image img = Image.getInstance(thumbFile.bytes)
                    img.setBorder(Image.BOX)
                    img.setBorderWidth(borderWidth)
                    img.setBorderColor(BaseColor.WHITE)
                    img.setAlignment(Element.ALIGN_CENTER)

                    img.scaleToFit(10000, (float)Utilities.millimetersToPoints(PHOTO_WIDTH) - 2 * borderWidth)
                    table.addCell(new PdfPCell(img))
                } else {
                    //add empty cell
                    table.addCell('No image')
                }
            }

            table.widthPercentage = 100
            document.add(table)
            document.close()
            return baos
        } catch (DocumentException dex) {
            log.error('Error in pdf export', dex)
        }
    }

    ByteArrayOutputStream generateCardDoc(Card card, File destinationDir, Locale locale) {
        try {
            def document = new Document()
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
    //        PdfWriter.getInstance(document, new FileOutputStream("/tmp/hello.pdf"))//.setStrictImageSequence(true)
            PdfWriter.getInstance(document, baos)//.setStrictImageSequence(true)
            document.open()
            PdfPTable table = new PdfPTable(2)
            table.setWidths([1f, 4f] as float[])

            //Identification section
            table.addCell(new Phrase('Inventario'))
            table.addCell(new Phrase(card.inventoryNumber as String))
            table.addCell(new Phrase('Fondo'))
            table.addCell(new Phrase(card.fund.name))
            table.addCell(new Phrase('Segnatura'))
            table.addCell(new Phrase(card.signature))
            table.addCell(new Phrase('Cronologia'))
            table.addCell(new Phrase(card.dating?: ''))

            //Authors section
            table.addCell(new Phrase('Autore'))
            table.addCell(new Phrase(card.authorNotFound? 'non trovato': card.author))
            table.addCell(new Phrase('Stampatore'))
            table.addCell(new Phrase(card.printer ?: ''))
            table.addCell(new Phrase('Editore'))
            table.addCell(new Phrase(card.editor ?: ''))

            //Description section
            table.addCell(new Phrase('Titolo'))
            table.addCell(new Phrase(card.title))
            table.addCell(new Phrase('Soggetto'))
            table.addCell(new Phrase(card.subject))
            table.addCell(new Phrase('Iscrizioni'))
            table.addCell(new Phrase(card.inscription))

            //Technical description
            table.addCell(new Phrase(messageSource.getMessage('card.object.label', [] as Object[], locale)))
            table.addCell(new Phrase(card.object ? messageSource.getMessage("card.object.${card.object}" as String, [] as Object[], locale): ''))
            table.addCell(new Phrase('Indicazione colore'))
            String translatedColorIndication = card.colorIndication ? messageSource.getMessage("card.colorIndication.${card.colorIndication}" as String, [] as Object[], locale): ''
            table.addCell(new Phrase(translatedColorIndication))
            table.addCell(new Phrase( messageSource.getMessage('card.technique.label' as String, [] as Object[], locale)))
            String translatedTechnique = card.technique ? messageSource.getMessage("card.technique.${card.technique}" as String, [] as Object[], locale): ''
            table.addCell(new Phrase(translatedTechnique))
            table.addCell(new Phrase('Supporto primario'))
            String translatedPrimary = card.primarySupport ? messageSource.getMessage("card.primarySupport.${card.primarySupport}" as String, [] as Object[], locale): ''
            def primaryPhrase = new Phrase(translatedPrimary + ' ')
            if (card.primarySupportDimensions && card.primarySupportDimensions.height) {
                primaryPhrase.add(new Chunk(card.primarySupportDimensions.height as String))
                primaryPhrase.add(new Chunk(' h (mm) '))
                primaryPhrase.add(new Chunk(card.primarySupportDimensions.width as String))
                primaryPhrase.add(new Chunk(' l (mm) '))
            }
            table.addCell(primaryPhrase)

            if (card.secondarySupport && card.secondarySupport != 'not_present') {
                table.addCell(new Phrase("Supporto secondario"))
                String translatedSecondary = card.secondarySupport ? messageSource.getMessage("card.secondarySupport.${card.secondarySupport}" as String, [] as Object[], locale): ''
                def secondaryPhrase = new Paragraph(translatedSecondary + ' ')
                if (card.secondarySupportDimensions && card.secondarySupportDimensions.height) {
                    secondaryPhrase.add(new Chunk(card.secondarySupportDimensions.height as String))
                    secondaryPhrase.add(new Chunk(' h (mm) '))
                    secondaryPhrase.add(new Chunk(card.secondarySupportDimensions.width as String))
                    secondaryPhrase.add(new Chunk(' l (mm) '))
                }
                table.addCell(secondaryPhrase)
            }

            table.addCell(new Phrase(messageSource.getMessage('card.conservationStatus.label', [] as Object[], locale)))
            String translatedConservationStatus = messageSource.getMessage("card.conservationStatus.${card.conservationStatus}" as String, [] as Object[], locale)
            table.addCell(new Paragraph(translatedConservationStatus))

            table.addCell(new Phrase(messageSource.getMessage('card.statusDetails.label', [] as Object[], locale)))
            table.addCell(new Phrase(card.statusDetails))


            //Other section
            PdfPCell cell = new PdfPCell(new Phrase("Bibliografia"))
            cell.setRowspan(card.bibliography.size())
            table.addCell(cell)
            for (String nota in card.bibliography) {
                table.addCell(new Phrase(nota))
            }

            cell = new PdfPCell(new Phrase(messageSource.getMessage('card.sources.label', [] as Object[], locale)))
            cell.setRowspan(card.sources.size())
            table.addCell(cell)
            for (String ref in card.sources) {
                table.addCell(new Phrase(ref))
            }
            table.addCell(new Phrase('Osservazioni'))
            table.addCell(new Phrase(card.observations))

            table.addCell(new Phrase('Nome compilatore'))
            table.addCell(new Phrase(card.creator.username))

            table.widthPercentage = 100
            document.add(table)

            //add all images
            int imagePerRow = 2
            float pageWidth = document.pageSize.width
            float availableWidth = pageWidth - (document.pageSize.borderWidthLeft + document.pageSize.borderWidthRight)
            float imageBorder = 10
            availableWidth = availableWidth - (imagePerRow * 2 * imageBorder) - 40 //40 hid some missed border

            float scaledWidThSpace = availableWidth / imagePerRow
            int imgPerParagraphCounter = 0
            Paragraph p = new Paragraph()
            for (StoredImage stImage in card.images) {
                File thumbFile = new File(destinationDir, "${stImage.fileName}_thumb.png")
                Image img = Image.getInstance(thumbFile.bytes)
                img.setBorder(Image.BOX)
                img.setBorderWidth(imageBorder)
                img.setBorderColor(BaseColor.WHITE)
                img.scaleToFit(scaledWidThSpace, 10000)
                def c = new Chunk(img, 0, 0, true)
                p.add(c)
                imgPerParagraphCounter++
                if ((imgPerParagraphCounter % imagePerRow) == 0) {
                    document.add(p)
                    p = new Paragraph()
                }
            }
            document.add(p)
            document.close()
            return baos
        } catch (DocumentException dex) {
            log.error('Error in pdf export', dex)
        }
    }

    def generateCardDocOld(Card card, File destinationDir) {
        //TODO localizzare tutte le stringhe dalla classe di dominio
        def document = new Document()
        PdfWriter.getInstance(document, new FileOutputStream("/tmp/hello.pdf"))
        document.open()

        //Identification section
        addSectionHeader('Identificazione', document)

        def p = new Paragraph()
        p.add(new Phrase("Inventario: ${card.inventoryNumber}"))
        p.add("             ")
        p.add(new Phrase("Fondo: ${card.fund.name}"))
        document.add(p)

        p = new Paragraph()
        p.add(new Phrase("Segnatura: ${card.signature}"))
        p.add("             ")
        p.add(new Phrase("Cronologia: ${card.dating?: '' }"))
        document.add(p)

        //Authors section
        addSectionHeader('Autori', document)

        p = new Paragraph()
        p.add(new Phrase("Autore: ${card.authorNotFound? 'non trovato': card.author}"))
        p.add("             ")
        p.add(new Phrase("Stampatore: ${card.printer ?: ''}"))
        p.add("             ")
        p.add(new Phrase("Editore: ${card.editor ?: ''}"))
        document.add(p)

        //Description section
        addSectionHeader('Descrizione', document)
        p = new Paragraph()
        p.add(new Phrase("Titolo: ${card.title}"))
        p.add(Chunk.NEWLINE)
        document.add(p)

        p = new Phrase('Titolo')
        document.add(p)
        p = new Paragraph(card.title)
        p.setIndentationLeft(14)
        document.add(p)

        p = new Phrase('Iscrizioni')
        document.add(p)
        p = new Paragraph(card.inscription)
        p.setIndentationLeft(14)
        document.add(p)

        //Technical description
        addSectionHeader('Descrizione tecnica', document)
        p = new Paragraph()
        p.add(new Phrase("Oggetto: ${card.object ?: ''}"))
        p.add("             ")
        p.add(new Phrase("Indicazione colore: ${card.colorIndication ?: ''}"))
        p.add("             ")
        p.add(Chunk.NEWLINE)
        p.add(new Phrase("Tecnica fotografica: ${card.technique ?: ''}"))
        p.add("             ")
        p.add(new Phrase("Supporto primario: ${card.primarySupport} "))
        if (card.primarySupportDimensions && card.primarySupportDimensions.height) {
            p.add(new Chunk(card.primarySupportDimensions.height as String))
            p.add(new Chunk(' h (mm) '))
            p.add(new Chunk(card.primarySupportDimensions.width as String))
            p.add(new Chunk(' l (mm) '))
        }
        document.add(p)

        if (card.secondarySupport && card.secondarySupport != 'not_present') {
            p = new Paragraph()
            p.add(new Phrase("Supporto secondario: ${card.secondarySupport} "))
            if (card.secondarySupportDimensions && card.secondarySupportDimensions.height) {
                p.add(new Chunk(card.secondarySupportDimensions.height as String))
                p.add(new Chunk(' h (mm) '))
                p.add(new Chunk(card.secondarySupportDimensions.width as String))
                p.add(new Chunk(' l (mm) '))
            }
            document.add(p)
        }
        p = new Phrase(' Dettagli stato di conservazione')
        document.add(p)
        p = new Paragraph(card.statusDetails)
        p.setIndentationLeft(14)
        document.add(p)

        //Other section
        addSectionHeader('Altro', document)
        document.add(new Phrase("Bibliografia:"))
        document.add(Chunk.NEWLINE)
        for (String nota in card.bibliography) {
            p = new Paragraph(nota)
            p.setIndentationLeft(14)
            document.add(p)
        }

        document.add(new Phrase("Documenti di riferimento:"))
        document.add(Chunk.NEWLINE)
        for (String ref in card.sources) {
            p = new Paragraph(ref)
            p.setIndentationLeft(14)
            document.add(p)
        }

        document.add(new Phrase("Osservazioni:"))
        document.add(Chunk.NEWLINE)
        p = new Paragraph(card.observations)
        p.setIndentationLeft(14)
        document.add(p)

        //add all images
        for (StoredImage stImage in card.images) {
            File thumbFile = new File(destinationDir, "${stImage.fileName}_thumb.png")
            document.add(Image.getInstance(thumbFile.bytes))
        }

        document.close()
    }


    private addSectionHeader(String name, Document document) {
        def sectionHeader = new Phrase(name, BOLD)
        document.add(sectionHeader)
        document.add(Chunk.NEWLINE)
    }
}

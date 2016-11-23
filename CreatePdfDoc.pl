#!/usr/bin/perl -w
use strict;

# See: http://search.cpan.org/~ssimms/PDF-API2-2.030/lib/PDF/API2.pm

use PDF::API2;

my $f_nPictureWidth = 400;
my $f_nPictureHeight = 480;

my $f_nXTransformOffset = 480;

my $f_nYColoumn2 = -250;
my $f_nXColoumn2 = 400;


sub add_png{
  my $pdf = shift;
  my $page = shift;
  my $png = shift;
  my $nXorg = shift;
  my $nYorg = shift;

  my $image = $pdf->image_png($png);
  #$page->mediabox($nXorg,$nYorg,$f_nPictureWidth, $f_nPictureHeight);
  #$page->trimbox($nXorg,$nYorg,$f_nPictureWidth, $f_nPictureHeight);
  my $gfx = $page->gfx;
#  $gfx->transform( -rotate    => 90, -translate => [my $_x = 600, my $_y = 0], );
  $gfx->image($image, $nXorg, $nYorg, $f_nPictureWidth, $f_nPictureHeight);

}

sub SectionA {
  my $pPdf = shift;
  my $pPage = shift;
  my $szPicture = shift;
  
  add_png($pPdf, $pPage, $szPicture, 0, 0);
}
sub SectionB {
  my $pPdf = shift;
  my $pPage = shift;
  my $szPicture = shift;
  
  add_png($pPdf, $pPage, $szPicture, 0, $f_nYColoumn2);
}
sub SectionC {
  my $pPdf = shift;
  my $pPage = shift;
  my $szPicture = shift;
  
  add_png($pPdf, $pPage, $szPicture, $f_nXColoumn2, 0);
}
sub SectionD {
  my $pPdf = shift;
  my $pPage = shift;
  my $szPicture = shift;
  
  add_png($pPdf, $pPage, $szPicture, $f_nXColoumn2, $f_nYColoumn2);
}

# Create a blank PDF file
my $pdf = PDF::API2->new();

my %h = $pdf->info(
        'Author'       => "",
        'CreationDate' => "D:20161123053200+01'00'",
        'ModDate'      => "D:YYYYMMDDhhmmssOHH'mm'",
        'Creator'      => "GameControllerMap.pl",
        'Producer'     => "PDF::API2",
        'Title'        => "Game Controller Map",
        'Subject'      => "XBox Game Controller mappings",
        'Keywords'     => "xbox game controller mapping"
    );

# Set the page size
$pdf->mediabox('A4');

# Add a blank page
my $page = $pdf->page();

my $gfx = $page->gfx;
$gfx->transform( -rotate    => 90, -translate => [my $_x = $f_nXTransformOffset, my $_y = 0], );

SectionA($pdf, $page, "maps/007_qs.png");
SectionB($pdf, $page, "maps/bf2_foot.png");
SectionC($pdf, $page, "maps/bf2_helicopter.png");
SectionD($pdf, $page, "maps/bf2_vehicle.png");

# Add a blank page
my $page2 = $pdf->page();
my $gfx2 = $page2->gfx;
$gfx2->transform( -rotate    => 90, -translate => [$_x = $f_nXTransformOffset, $_y = 0], );

SectionA($pdf, $page2, "maps/brutal_legend_foot.png");
SectionB($pdf, $page2, "maps/brutal_legend_stage_battles.png");
SectionC($pdf, $page2, "maps/brutal_legend_vehicle.png");
SectionD($pdf, $page2, "maps/aot.png");

# Add a blank page
my $page3 = $pdf->page();
my $gfx3 = $page3->gfx;
$gfx3->transform( -rotate    => 90, -translate => [$_x = $f_nXTransformOffset, $_y = 0], );
SectionA($pdf, $page3, "maps/fme.png");
SectionB($pdf, $page3, "maps/gow3.png");
SectionC($pdf, $page3, "maps/graw.png");
SectionD($pdf, $page3, "maps/halo3.png");

# Add a blank page
my $page4 = $pdf->page();
my $gfx4 = $page4->gfx;
$gfx4->transform( -rotate    => 90, -translate => [$_x = $f_nXTransformOffset, $_y = 0], );
SectionA($pdf, $page4, "maps/mw3.png");
SectionB($pdf, $page4, "maps/pgr4.png");
SectionC($pdf, $page4, "maps/titanfall_pilot.png");
SectionD($pdf, $page4, "maps/titanfall_titan.png");

# Add a blank page
my $page5 = $pdf->page();
my $gfx5 = $page5->gfx;
$gfx5->transform( -rotate    => 90, -translate => [$_x = $f_nXTransformOffset, $_y = 0], );

SectionA($pdf, $page5, "maps/tron_foot.png");
SectionB($pdf, $page5, "maps/tron_cycle.png");
SectionC($pdf, $page5, "maps/tron_tank.png");


# Save the PDF
$pdf->saveas('ControllerMaps.pdf');

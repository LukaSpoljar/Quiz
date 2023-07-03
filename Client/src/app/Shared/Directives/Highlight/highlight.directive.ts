import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {

  private readonly hoveredColor: string = 'rgba(0,0,0,0.1)';

  constructor(private _elementRef: ElementRef) {}

  @HostListener('pointerenter') onPointerEnter(): void { this.highlight(this.hoveredColor); }
  @HostListener('pointerleave') onPointerLeave(): void { this.highlight(''); }

  private highlight(color:string): void { this._elementRef.nativeElement.style.backgroundColor = color; }

}

import { NgModule } from '@angular/core';

import { AutocompletedtooptimSharedLibsModule, FindLanguageFromKeyPipe, AudAlertComponent, AudAlertErrorComponent } from './';

@NgModule({
    imports: [AutocompletedtooptimSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, AudAlertComponent, AudAlertErrorComponent],
    exports: [AutocompletedtooptimSharedLibsModule, FindLanguageFromKeyPipe, AudAlertComponent, AudAlertErrorComponent]
})
export class AutocompletedtooptimSharedCommonModule {}

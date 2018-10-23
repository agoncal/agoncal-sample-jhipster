import { NgModule } from '@angular/core';

import { AutocompletedtoSharedLibsModule, FindLanguageFromKeyPipe, AudAlertComponent, AudAlertErrorComponent } from './';

@NgModule({
    imports: [AutocompletedtoSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, AudAlertComponent, AudAlertErrorComponent],
    exports: [AutocompletedtoSharedLibsModule, FindLanguageFromKeyPipe, AudAlertComponent, AudAlertErrorComponent]
})
export class AutocompletedtoSharedCommonModule {}

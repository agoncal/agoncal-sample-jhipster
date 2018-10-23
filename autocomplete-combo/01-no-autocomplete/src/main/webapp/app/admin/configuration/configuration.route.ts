import { Route } from '@angular/router';

import { NoaConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'noa-configuration',
    component: NoaConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
